package biz.ostw.ee.config;

/**
 * @author mathter
 */
public interface ConfigService
{
    public < T > T get( Object key, Class< T > clazz ) throws ConvertorNotFound;

    public < T > T get( Object key, Class< T > clazz, T defaultValue ) throws ConvertorNotFound;

    public < T > T get( Object key ) throws ConvertorNotFound;

    public < T > T get( Object key, T defaultValue ) throws ConvertorNotFound;

    public String getString( Object key ) throws ConvertorNotFound;

    public String getString( Object key, String defaultValue ) throws ConvertorNotFound;

    public Byte getByte( Object key ) throws ConvertorNotFound;

    public Byte getByte( Object key, Byte defaultValue ) throws ConvertorNotFound;

    public Short getShort( Object key ) throws ConvertorNotFound;

    public Short getShort( Object key, Short defaultValue ) throws ConvertorNotFound;

    public Integer getInteger( Object key ) throws ConvertorNotFound;

    public Integer getInteger( Object key, Integer defaultValue ) throws ConvertorNotFound;

    public Long getLong( Object key ) throws ConvertorNotFound;

    public Long getLong( Object key, Long defaultValue ) throws ConvertorNotFound;

    public Float getFloat( Object key ) throws ConvertorNotFound;

    public Float getFloat( Object key, Float defaultValue ) throws ConvertorNotFound;

    public Double getDouble( Object key ) throws ConvertorNotFound;

    public Double getDouble( Object key, Double defaultValue ) throws ConvertorNotFound;

    public < T > void put( Object key, T value ) throws NullPointerException, IllegalArgumentException, ConvertorNotFound;

    public < T > void put( Object key, T value, Class< ? extends T > clazz )
        throws NullPointerException, IllegalArgumentException, ConvertorNotFound;
}
