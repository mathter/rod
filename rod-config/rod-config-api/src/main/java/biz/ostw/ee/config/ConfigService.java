package biz.ostw.ee.config;

/**
 * @author mathter
 */
public interface ConfigService
{
    public < T > T get( Object key, Class< T > clazz );

    public < T > T get( Object key, Class< T > clazz, T defaultValue );

    public < T > T get( Object key );

    public < T > T get( Object key, T defaultValue );

    public String getString( Object key );

    public String getString( Object key, String defaultValue );

    public Byte getByte( Object key );

    public Byte getByte( Object key, Byte defaultValue );

    public Short getShort( Object key );

    public Short getShort( Object key, Short defaultValue );

    public Integer getInteger( Object key );

    public Integer getInteger( Object key, Integer defaultValue );

    public Long getLong( Object key );

    public Long getLong( Object key, Long defaultValue );

    public Float getFloat( Object key );

    public Float getFloat( Object key, Float defaultValue );

    public Double getDouble( Object key );

    public Double getDouble( Object key, Double defaultValue );
}
