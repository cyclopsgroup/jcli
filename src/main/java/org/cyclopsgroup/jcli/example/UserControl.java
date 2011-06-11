package org.cyclopsgroup.jcli.example;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.cyclopsgroup.caff.conversion.DateField;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.annotation.Argument;
import org.cyclopsgroup.jcli.annotation.Cli;
import org.cyclopsgroup.jcli.annotation.MultiValue;
import org.cyclopsgroup.jcli.annotation.Option;

/**
 * An example bean that shows how JCli populates POJO with arguments
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Cli( name = "user", description = "Comamnd line tool that manages user accounts" )
public class UserControl
{
    private UserControlAction action = UserControlAction.DISPLAY;

    private Date creationDate;

    private int intValue;

    private List<String> userNames;

    /**
     * @return Enum field example
     */
    @Option( name = "a", longName = "action", description = "Action to perform" )
    public final UserControlAction getAction()
    {
        return action;
    }

    /**
     * @return A date example that needs customized conversion rule
     */
    @DateField( format = "yyyyMMdd" )
    @Option( name = "d", longName = "date", description = "Start date" )
    public Date getCreationDate()
    {
        return creationDate;
    }

    /**
     * @return A meaningless integer value
     */
    @Option( name = "l", longName = "level", description = "A meaningless integer value" )
    public final int getIntValue()
    {
        return intValue;
    }

    /**
     * @return Multi value string field example
     */
    @MultiValue
    @Argument( description = "User account name" )
    public final List<String> getUserNames()
    {
        return userNames;
    }

    /**
     * @param action Enum example
     */
    public final void setAction( UserControlAction action )
    {
        this.action = action;
    }

    /**
     * @param creationDate A date example that needs customized conversion rule
     */
    public void setCreationDate( Date creationDate )
    {
        this.creationDate = creationDate;
    }

    /**
     * @param intValue A meaningless integer value
     */
    public final void setIntValue( int intValue )
    {
        this.intValue = intValue;
    }

    /**
     * @param userNames Multi value string field example
     */
    public final void setUserNames( List<String> userNames )
    {
        this.userNames = userNames;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString( this );
    }

    /**
     * @param args Command line arguments
     */
    public static void main( String[] args )
    {
        UserControl control = new UserControl();
        ArgumentProcessor.newInstance( UserControl.class ).process( args, control );
        System.out.println( control );
    }
}
