package com.ebs.common.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ebs.common.utils.StringUtils;


public class JSONObject extends LinkedHashMap<String, Object>
{
    private static final long serialVersionUID = 1L;
    private static final Pattern arrayNamePattern = Pattern.compile("(\\w+)((\\[\\d+\\])+)");
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static class JSONArray extends ArrayList<Object>
    {
        private static final long serialVersionUID = 1L;

        public JSONArray()
        {
            super();
        }

        public JSONArray(int size)
        {
            super(size);
        }

        @Override
        public String toString()
        {
            try
            {
                return JSON.marshal(this);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Object set(int index, Object element)
        {
            return super.set(index, transfer(element));
        }

        @Override
        public boolean add(Object element)
        {
            return super.add(transfer(element));
        }

        @Override
        public void add(int index, Object element)
        {
            super.add(index, transfer(element));
        }
    }

    public JSONObject()
    {
        super();
    }

    public JSONObject(final JSONObject other)
    {
        super(other);
    }

    @Override
    public String toString()
    {
        try
        {
            return JSON.marshal(this);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    public String toCompactString()
    {
        try
        {
            return objectMapper.writeValueAsString(this);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    public Integer intValue(final String name)
    {
        return valueAsInt(value(name));
    }


    public Integer intValue(final String name, final Integer defaultValue)
    {
        return StringUtils.nvl(intValue(name), defaultValue);
    }


    public Long longValue(final String name)
    {
        return valueAsLong(value(name));
    }


    public Long longValue(final String name, final Long defaultValue)
    {
        return StringUtils.nvl(longValue(name), defaultValue);
    }


    public Boolean boolValue(final String name)
    {
        return valueAsBool(value(name));
    }


    public Boolean boolValue(final String name, final Boolean defaultValue)
    {
        return StringUtils.nvl(boolValue(name), defaultValue);
    }


    public String strValue(final String name)
    {
        return valueAsStr(value(name));
    }


    public String strValue(final String name, final String defaultValue)
    {
        return StringUtils.nvl(strValue(name), defaultValue);
    }


    public Object value(final String name)
    {
        final int indexDot = name.indexOf('.');
        if (indexDot >= 0)
        {
            return obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1));
        }
        else
        {
            final Matcher matcher = arrayNamePattern.matcher(name);
            if (matcher.find())
            {
                return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Object>()
                {
                    @Override
                    public Object callback(JSONArray arr, int index)
                    {
                        return elementAt(arr, index);
                    }
                });
            }
            else
            {
                return get(name);
            }
        }
    }


    public JSONObject value(final String name, final Object value)
    {
        final int indexDot = name.indexOf('.');
        if (indexDot >= 0)
        {
            obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1), value);
        }
        else
        {
            final Matcher matcher = arrayNamePattern.matcher(name);
            if (matcher.find())
            {
                endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Void>()
                {
                    @Override
                    public Void callback(JSONArray arr, int index)
                    {
                        elementAt(arr, index, value);
                        return null;
                    }
                });
            }
            else
            {
                set(name, value);
            }
        }
        return this;
    }


    public JSONObject obj(final String name)
    {
        final Matcher matcher = arrayNamePattern.matcher(name);
        if (matcher.find())
        {
            return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<JSONObject>()
            {
                @Override
                public JSONObject callback(JSONArray arr, int index)
                {
                    return objAt(arr, index);
                }
            });
        }
        else
        {
            JSONObject obj = getObj(name);
            if (obj == null)
            {
                obj = new JSONObject();
                put(name, obj);
            }
            return obj;
        }
    }


    public JSONArray arr(final String name)
    {
        JSONArray arr = getArr(name);
        if (arr == null)
        {
            arr = new JSONArray();
            put(name, arr);
        }
        return arr;
    }


    public JSONObject getObj(final String name)
    {
        return (JSONObject) get(name);
    }


    public JSONArray getArr(final String name)
    {
        return (JSONArray) get(name);
    }


    public Integer getInt(final String name)
    {
        return valueAsInt(get(name));
    }


    public Integer getInt(final String name, Integer defaultValue)
    {
        return StringUtils.nvl(getInt(name), defaultValue);
    }


    public Long getLong(final String name)
    {
        return valueAsLong(get(name));
    }


    public Long getLong(final String name, Long defaultValue)
    {
        return StringUtils.nvl(getLong(name), defaultValue);
    }


    public String getStr(final String name)
    {
        return valueAsStr(get(name));
    }


    public String getStr(final String name, final String defaultValue)
    {
        return StringUtils.nvl(getStr(name), defaultValue);
    }


    public Boolean getBool(final String name)
    {
        return valueAsBool(get(name));
    }


    public Boolean getBool(final String name, final Boolean defaultValue)
    {
        return StringUtils.nvl(getBool(name), defaultValue);
    }


    public JSONObject set(final String name, final Object value)
    {
        put(name, value);
        return this;
    }


    public <T> T asBean(Class<T> beanClass)
    {
        try
        {
            return JSON.unmarshal(JSON.marshal(this), beanClass);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Object put(String key, Object value)
    {
        return super.put(key, transfer(value));
    }

    public static Integer valueAsInt(Object value)
    {
        if (value instanceof Integer)
        {
            return (Integer) value;
        }
        else if (value instanceof Number)
        {
            return ((Number) value).intValue();
        }
        else if (value instanceof String)
        {
            return Integer.valueOf((String) value);
        }
        else if (value instanceof Boolean)
        {
            return ((Boolean) value) ? 1 : 0;
        }
        else
        {
            return null;
        }
    }

    public static Long valueAsLong(Object value)
    {
        if (value instanceof Long)
        {
            return (Long) value;
        }
        else if (value instanceof Number)
        {
            return ((Number) value).longValue();
        }
        else if (value instanceof String)
        {
            return Long.valueOf((String) value);
        }
        else if (value instanceof Boolean)
        {
            return ((Boolean) value) ? 1L : 0L;
        }
        else
        {
            return null;
        }
    }

    public static String valueAsStr(Object value)
    {
        if (value instanceof String)
        {
            return (String) value;
        }
        else if (value != null)
        {
            return value.toString();
        }
        else
        {
            return null;
        }
    }

    public static Boolean valueAsBool(Object value)
    {
        if (value instanceof Boolean)
        {
            return (Boolean) value;
        }
        else if (value instanceof Number)
        {
            return ((Number) value).doubleValue() != 0.0;
        }
        else if (value instanceof String)
        {
            return Boolean.valueOf((String) value);
        }
        else
        {
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    private static Object transfer(final Object value)
    {
        if (!(value instanceof JSONObject) && value instanceof Map)
        {
            return toObj((Map<String, Object>) value);
        }
        else if (!(value instanceof JSONArray) && value instanceof Collection)
        {
            return toArr((Collection<Object>) value);
        }
        else
        {
            return value;
        }
    }

    private static JSONArray toArr(final Collection<Object> list)
    {
        final JSONArray arr = new JSONArray(list.size());
        for (final Object element : list)
        {
            arr.add(element);
        }
        return arr;
    }

    private static JSONObject toObj(final Map<String, Object> map)
    {
        final JSONObject obj = new JSONObject();
        for (final Map.Entry<String, Object> ent : map.entrySet())
        {
            obj.put(ent.getKey(), transfer(ent.getValue()));
        }
        return obj;
    }


    private static JSONArray arrayAt(JSONArray arr, int index)
    {
        expand(arr, index);
        if (arr.get(index) == null)
        {
            arr.set(index, new JSONArray());
        }
        return (JSONArray) arr.get(index);
    }


    private static JSONObject objAt(final JSONArray arr, int index)
    {
        expand(arr, index);
        if (arr.get(index) == null)
        {
            arr.set(index, new JSONObject());
        }
        return (JSONObject) arr.get(index);
    }


    private static void elementAt(final JSONArray arr, final int index, final Object value)
    {
        expand(arr, index).set(index, value);
    }


    private static Object elementAt(final JSONArray arr, final int index)
    {
        return expand(arr, index).get(index);
    }


    private static JSONArray expand(final JSONArray arr, final int index)
    {
        while (arr.size() <= index)
        {
            arr.add(null);
        }
        return arr;
    }


    private interface EndArrayCallback<T>
    {

        T callback(JSONArray arr, int index);
    }


    private <T> T endArray(final String name, final String indexesStr, final EndArrayCallback<T> callback)
    {
        JSONArray endArr = arr(name);
        final int[] indexes = parseIndexes(indexesStr);
        int i = 0;
        while (i < indexes.length - 1)
        {
            endArr = arrayAt(endArr, indexes[i++]);
        }
        return callback.callback(endArr, indexes[i]);
    }

    private static int[] parseIndexes(final String s)
    {
        int[] indexes = null;
        List<Integer> list = new ArrayList<Integer>();

        final StringTokenizer st = new StringTokenizer(s, "[]");
        while (st.hasMoreTokens())
        {
            final int index = Integer.valueOf(st.nextToken());
            if (index < 0)
            {
                throw new RuntimeException(String.format("Illegal index %1$d in \"%2$s\"", index, s));
            }

            list.add(index);
        }

        indexes = new int[list.size()];
        int i = 0;
        for (Integer tmp : list.toArray(new Integer[list.size()]))
        {
            indexes[i++] = tmp;
        }

        return indexes;
    }
}
