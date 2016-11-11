package biz.ostw.persistence;

import java.io.Serializable;

import com.querydsl.core.types.Predicate;

public interface WhereClause< C extends WhereClause< C > > extends Serializable
{
    public C where( Predicate ... predicates );
    
    public Predicate[] where();
}
