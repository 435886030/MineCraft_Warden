package joptsimple.examples;
 
import static java.util.Arrays.*;
import static java.util.Collections.*;
 
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.junit.Test;
import static org.junit.Assert.*;
 
public class ShortOptionsWithArgumentsTest {
    public static void main(String[] args)
    {
        OptionParser parser = new OptionParser();
        parser.accepts( "flag" );
        parser.accepts( "verbose" );
 
        OptionSet options = parser.parse( "--flag" );
 
        assertTrue( options.has( "flag" ) );
        assertFalse( options.has( "verbose" ) );
    }

}