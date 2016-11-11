package biz.ostw.persistence.jpa;

import java.io.Serializable;

import com.querydsl.core.types.EntityPath;

import biz.ostw.persistence.SingleEntityPath;

public class AbstractSingleEntity< T > implements SingleEntityPath< T >, Serializable
{
    private static final long serialVersionUID = -7453675650548609112L;

    protected EntityPath< T > entityPath;

    protected AbstractSingleEntity( final EntityPath< T > entityPath )
    {
        this.entityPath = entityPath;
    }

    public EntityPath< T > getEntityPath()
    {
        return this.entityPath;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "AbstractSingleEntityImpl [entityPath=" );
        builder.append( entityPath );
        builder.append( "]" );
        return builder.toString();
    }
}