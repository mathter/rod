package biz.ostw.persistence;

import com.querydsl.core.types.OrderSpecifier;

public interface OrderByClause< C extends OrderByClause< C > >
{
    public C orderBy( OrderSpecifier< ? > ... orders );
    
    public OrderSpecifier< ? >[] orderBy();
}
