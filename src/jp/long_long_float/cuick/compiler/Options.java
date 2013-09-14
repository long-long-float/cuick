package jp.long_long_float.cuick.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

@Getter
public class Options {

    @Option(name="-o", usage="place the output into this file")
    private File outputFile;
    
    @Option(name="-h", aliases="--help", usage="print this help")
    private boolean helpFlag;
    
    @Option(name="-v", aliases="--version", usage="print version")
    private boolean varsionFlag;
    
    @Option(name="-w", aliases="--with-compile", usage="compile use c++ compiler")
    private boolean withCompileFlag;
    
    @Option(name="--stdin", usage="read from stdin source")
    private boolean stdinFlag;
    
    @Option(name="--stdout", usage="write to stdout source")
    private boolean stdoutFlag;
    
    @Argument(metaVar="source", usage="source file")
    private List<String> sourceFiles = new ArrayList<String>();
    
    private CmdLineParser parser;
    
    public static Options parse(String[] args) throws CmdLineException {
        Options opts = new Options();
        opts.parser = new CmdLineParser(opts);
        opts.parser.parseArgument(args);
        return opts;
    }

}
