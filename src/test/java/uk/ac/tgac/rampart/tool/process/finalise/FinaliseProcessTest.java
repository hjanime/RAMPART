package uk.ac.tgac.rampart.tool.process.finalise;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.ac.ebi.fgpt.conan.core.context.DefaultExecutionContext;
import uk.ac.ebi.fgpt.conan.service.exception.ProcessExecutionException;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 20/11/13
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
public class FinaliseProcessTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void test1() throws InterruptedException, ProcessExecutionException, IOException {

        File outputDir = temp.newFolder("test1");

        File test1File = FileUtils.toFile(this.getClass().getResource("/tools/finalise/test1.fa"));

        FinaliseArgs args = new FinaliseArgs();
        args.setInputFile(test1File);
        args.setOutputDir(outputDir);
        args.setMinN(5);
        args.setOutputPrefix("TGAC_TS_V1");

        FinaliseProcess process = new FinaliseProcess(args);

        boolean result = process.execute(new DefaultExecutionContext());

        assertTrue(result);


    }
}
