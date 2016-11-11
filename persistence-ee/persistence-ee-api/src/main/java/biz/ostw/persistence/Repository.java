package biz.ostw.persistence;

import java.util.List;

import com.querydsl.core.types.EntityPath;

public interface Repository
{
    public < I, T > T get( Class< T > entityClass, I primaryKey );

    public < T > List< T > get( SelectClause< T > selectClause );

    public < T > SelectClause< T > from( EntityPath< T > path );

    public < T > DeleteClause< T > delete( EntityPath< T > path );

    public long execute( DMLClause clause );

    public < T > T put( T entity );

    public void remove( Object ... entities );
}
