package biz.ostw.ee.vfs;

/**
 * @author mathter
 */
public final class VfsAccessUtil
{
    public final static short OWNER_READ = 0b00000000_00000001;

    public final static short OWNER_WRITE = 0b00000000_00000010;

    public final static short GROUP_READ = 0b00000000_00000100;

    public final static short GROUP_WRITE = 0b00000000_00001000;

    public final static short OTHER_READ = 0b00000000_00010000;

    public final static short OTHER_WRITE = 0b00000000_00100000;

    public static boolean ownerRead( short mode )
    {
        return ( mode & OTHER_READ ) != 0;
    }

    public static boolean ownerWrite( short mode )
    {
        return ( mode & OTHER_READ ) != 0;
    }

    public static boolean groupRead( short mode )
    {
        return ( mode & GROUP_READ ) != 0;
    }

    public static boolean groupWrite( short mode )
    {
        return ( mode & GROUP_WRITE ) != 0;
    }

    public static boolean otherRead( short mode )
    {
        return ( mode & OTHER_READ ) != 0;
    }

    public static boolean otherWrite( short mode )
    {
        return ( mode & OTHER_WRITE ) != 0;
    }

    public static short mode( short ... modes )
    {
        short mode = 0;

        if ( modes != null && modes.length > 0 )
        {

            for ( int i = 0; i < modes.length; i ++ )
            {
                mode |= modes[ i ];
            }

        }

        return mode;
    }

    public static boolean checkAny( short mode, short ... checkedModes )
    {
        if ( checkedModes != null && checkedModes.length > 0 )
        {
            for ( short checkedMode : checkedModes )
            {
                if ( ( mode & checkedMode ) != 0 )
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkAll( short mode, short ... checkedModes )
    {
        if ( checkedModes != null && checkedModes.length > 0 )
        {
            for ( short checkedMode : checkedModes )
            {
                if ( ( mode & checkedMode ) == 0 )
                {
                    return false;
                }
            }
        }

        return true;
    }
}
