package biz.ostw.persistence;

public interface EntityFactory
{
    public < T > T get( Class< T > entityClass ) throws InstantiationException, IllegalAccessException;
}
