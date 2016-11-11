package biz.ostw.persistence.jpa;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;

import biz.ostw.persistence.SelectClause;


public class SingleSelectClauseImpl< T > extends AbstractSingleWhereClause< T, SelectClause< T > > implements SelectClause< T >
{
    private static final long serialVersionUID = -6776517106566183052L;

    private OrderSpecifier< ? >[] orderSpecifiers;

    private Expression< ? >[] groupByExpressions;

    protected SingleSelectClauseImpl( EntityPath< T > entityPath )
    {
        super( entityPath );
    }

    @Override
    public SingleSelectClauseImpl< T > orderBy( OrderSpecifier< ? > ... orders )
    {
        this.orderSpecifiers = orders;

        return this;
    }

    @Override
    public SingleSelectClauseImpl< T > groupBy( Expression< ? > ... expressions )
    {
        this.groupByExpressions = expressions;

        return this;
    }

    @Override
    public Expression< ? >[] groupBy()
    {
        return this.groupByExpressions;
    }

    @Override
    public OrderSpecifier< ? >[] orderBy()
    {
        return this.orderSpecifiers;
    }
}
