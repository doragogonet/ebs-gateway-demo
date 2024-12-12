package com.ebs.framework.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

// @ServerEndpoint webSocketエンドポイントを宣言して作成し、要求パスを示します
// id クライアントに要求されたときに搬送されるパラメータであり、サービス側がクライアントを区別して使用するために使用されます。
@ServerEndpoint("/ws/{sid}")
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    // 現在のオンライン接続数を記録する静的変数。スレッドが安全に設計されている必要があります。
    private static int onlineCount = 0;

    // 各クライアントに対応するWebSocketオブジェクトを格納するためのconcurrentパッケージのスレッドセキュリティ設定。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    // private static ConcurrentHashMap<String,WebSocketServer> websocketList = new ConcurrentHashMap<>();

    // クライアントとの接続セッションは、クライアントにデータを送信するために必要です
    private Session session;

    // 接收sid
    private String sid = "";

    /*
     * クライアントが接続を作成するときにトリガーされる
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this); // webSocketSetに追加
        addOnlineCount(); // オンライン数に+1
        log.info("リスニングを開始する新しい接続があります:" + sid + ", 現在のオンライン人数は" + getOnlineCount());
        this.sid = sid;
        try {
            this.sendMessage("接続成功");
        } catch (IOException e) {
            log.error("websocket IO 異常");
        }
    }

    /**
     * クライアント接続が停止するとトリガーされます
     **/
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // webSocketSetから削除
        subOnlineCount(); // オンライン数に-1
        log.info("接続が閉じることはあり！現在のオンライン人数は" + getOnlineCount());
    }

    /**
     * クライアント・メッセージを受信するとトリガーされます
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("クライアント：" + sid + "からの受信:" + message);
//        //クライアント毎に送信
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 接続異常発生時にトリガー
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("エラーがある");
        error.printStackTrace();
    }

    /**
     * サーバのアクティブプッシュの実装（ブラウザへのメッセージ送信）
     */
    private void sendMessage(String message) throws IOException {
        log.info(this.sid + ":サーバ・メッセージのプッシュ："+message);
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * すべてのクライアントにメッセージを送信
     * sidを指定すると指定されたクライアントにメッセージが送信されます
     * sidを指定しないとすべてのクライアントにメッセージが送信されます
     * */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        log.info("メッセージプッシュ：" + sid + "、内容：" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                // ここではこのsidだけにプッシュするものを設定することができ、nullであればすべてプッシュする
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}
