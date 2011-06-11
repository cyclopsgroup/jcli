package org.cyclopsgroup.jcli.spi;

/**
 * Model of a command line
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface Cli
{
    /**
     * @return Description of command line
     */
    String getDescription();

    /**
     * @return Name of command line
     */
    String getName();

    /**
     * @return Not of command line in the end
     */
    String getNote();
}
