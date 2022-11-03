package tabby.config;

import lombok.extern.slf4j.Slf4j;
import soot.G;
import soot.options.Options;

import java.io.File;

/**
 * @author wh1t3P1g
 * @since 2020/10/9
 */
@Slf4j
public class SootConfiguration {

    /**
     * soot 默认配置
     */
    public static void initSootOption(){
        String output = String.join(File.separator, System.getProperty("user.dir"), "temp");
        log.debug("Output directory: " + output);
        G.reset();
        soot.options.Options opt = Options.v();
        opt.set_verbose(true); // 打印详细信息

        opt.set_prepend_classpath(true); // 优先载入soot classpath
        opt.set_allow_phantom_refs(true);
        opt.set_keep_line_number(true); // 记录文件行数
        opt.set_src_prec(Options.src_prec_class); // 优先处理class格式
        opt.set_output_dir(output); // 设置IR Jimple的输出目录
        opt.set_output_format(Options.output_format_jimple); // 输出Jimple格式
//        opt.set_validate(true);
//        opt.set_ignore_classpath_errors(true); // Ignores invalid entries on the Soot classpath.
        opt.set_whole_program(true);// 目前开启过程间分析不会进行实质上的过程间分析，开启当前flag只是为了解决依赖缺失的问题
        opt.set_no_writeout_body_releasing(true); // 当输出内容后不释放获取的body数据
//        Options.v().set_no_bodies_for_excluded(true);
//        Options.v().set_omit_excepting_unit_edges(true);
        // 设置自定义的package
//        PhaseOptions.v().setPhaseOption("cg","on");

    }
}
