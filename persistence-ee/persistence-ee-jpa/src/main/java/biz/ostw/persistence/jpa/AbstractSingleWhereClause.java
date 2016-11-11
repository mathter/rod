package biz.ostw.persistence.jpa;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;

import biz.ostw.persistence.WhereClause;


public abstract class AbstractSingleWhereClause< T, C extends WhereClause< C > > extends AbstractSingleEntity< T >
    implements WhereClause< C >
{
    private static final long serialVersionUID = -4356438685639612094L;

    private Predicate[] wherePredicates;

    protected AbstractSingleWhereClause( EntityPath< T > entityPath )
    {
        super( entityPath );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public C where( Predicate ... predicates )
    {
        this.wherePredicates = predicates;

        return (C) this;
    }

    public Predicate[] where()
    {
        return this.wherePredicates;
    }
}
