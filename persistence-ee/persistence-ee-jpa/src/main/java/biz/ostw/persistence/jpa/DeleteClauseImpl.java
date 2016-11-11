package biz.ostw.persistence.jpa;

import com.querydsl.core.types.EntityPath;

import biz.ostw.persistence.DeleteClause;

public class DeleteClauseImpl< T > extends AbstractSingleWhereClause< T, DeleteClause< T > > implements DeleteClause< T >
{
    private static final long serialVersionUID = 5316705670109585126L;

    protected DeleteClauseImpl( EntityPath< T > entityPath )
    {
        super( entityPath );
    }
}
