package com.ebs.rfid.zebra;

public interface EventsListener {
	default public void commonReadNotify(String jsonStr) {}
}
