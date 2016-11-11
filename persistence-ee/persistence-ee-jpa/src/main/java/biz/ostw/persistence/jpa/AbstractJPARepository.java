package biz.ostw.persistence.jpa;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import biz.ostw.persistence.DMLClause;
import biz.ostw.persistence.DeleteClause;
import biz.ostw.persistence.EntityFactory;
import biz.ostw.persistence.Repository;
import biz.ostw.persistence.SelectClause;
import biz.ostw.persistence.SingleEntityPath;

public abstract class AbstractJPARepository implements Repository, EntityFactory
{
    protected abstract EntityManager getEntityManager();

    protected JPAQueryFactory getQueryFactory()
    {
        return new JPAQueryFactory( this.getEntityManager() );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public < T > T get( Class< T > entityClass ) throws InstantiationException, IllegalAccessException
    {
        T result = null;
        Metamodel metamodel = this.getEntityManager().getMetamodel();

        Set< EntityType< ? > > entityTypes = metamodel.getEntities();

        for ( EntityType< ? > entityType : entityTypes )
        {
            Class< ? > clazz = entityType.getJavaType();

            if ( entityClass.isAssignableFrom( clazz ) )
            {
                result = (T) clazz.newInstance();
            }
        }

        return result;
    }

    public < I, T > T get( Class< T > entityClass, I primaryKey )
    {
        return this.getEntityManager().find( entityClass, primaryKey );
    }

    @SuppressWarnings( "unchecked" )
    public < T > List< T > get( SelectClause< T > selectClause )
    {
        JPAQuery< T > query;

        if ( selectClause instanceof SingleEntityPath )
        {
            query = (JPAQuery< T >) this.getQueryFactory().from( ( (SingleEntityPath< T >) selectClause ).getEntityPath() );
        } else
        {
            throw new IllegalArgumentException( selectClause.getClass() + " is undefined!" );
        }

        setWhere( query, selectClause.where() );
        setOrderBy( query, selectClause.orderBy() );
        setGroupBy( query, selectClause.groupBy() );

        return query.fetch();
    }

    private static void setWhere( JPAQuery< ? > query, Predicate ... predicates )
    {
        if ( predicates != null && predicates.length > 0 )
        {
            query.where( predicates );
        }
    }

    private static void setOrderBy( JPAQuery< ? > query, OrderSpecifier< ? > ... orderSpecifiers )
    {
        if ( orderSpecifiers != null && orderSpecifiers.length > 0 )
        {
            query.orderBy( orderSpecifiers );
        }
    }

    private static void setGroupBy( JPAQuery< ? > query, Expression< ? > ... expressions )
    {
        if ( expressions != null && expressions.length > 0 )
        {
            query.groupBy( expressions );
        }
    }

    public < T > SelectClause< T > from( EntityPath< T > path )
    {
        return new SingleSelectClauseImpl< T >( path );
    }

    public < T > DeleteClause< T > delete( EntityPath< T > path )
    {
        return new DeleteClauseImpl< T >( path );
    }

    @SuppressWarnings( "rawtypes" )
    @Transactional( Transactional.TxType.REQUIRED )
    public long execute( DMLClause clause )
    {
        com.querydsl.core.dml.DMLClause< ? > dmlClause;

        if ( clause instanceof DeleteClause< ? > )
        {
            dmlClause = this.getQueryFactory().delete( ( (SingleEntityPath) clause ).getEntityPath() );
        } else
        {
            throw new IllegalArgumentException( clause.getClass() + " is undefined!" );
        }

        return dmlClause.execute();
    }

    @Override
    @Transactional( Transactional.TxType.REQUIRED )
    public < T > T put( T entity )
    {
        entity = this.getEntityManager().merge( entity );

        return entity;
    }

    @Override
    @Transactional( Transactional.TxType.REQUIRED )
    public void remove( Object ... entities )
    {
        EntityManager em = this.getEntityManager();
        
        if ( entities != null && entities.length > 0 )
        {
            for ( Object entity : entities )
            {
                entity = em.merge( entity );
                this.getEntityManager().remove( entity );
            }
        }
    }
}
