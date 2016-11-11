package biz.ostw.persistence;

import com.querydsl.core.types.Expression;

public interface GroupByClause< C extends GroupByClause< C > >
{
    public C groupBy( Expression< ? > ... expressions );

    public Expression< ? >[] groupBy();
}
