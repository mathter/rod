package biz.ostw.persistence;

public interface SelectClause< T >
    extends WhereClause< SelectClause< T > >, OrderByClause< SelectClause< T > >, GroupByClause< SelectClause< T > >
{

}