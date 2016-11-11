package biz.ostw.persistence;

import com.querydsl.core.types.EntityPath;

public interface SingleEntityPath< T >
{
    public EntityPath< T > getEntityPath();
}
