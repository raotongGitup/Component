package com.example.router_compiler.processor;

import com.example.router_annotation.Route;
import com.example.router_annotation.moudle.RouteMeta;
import com.example.router_compiler.utils.Consts;
import com.example.router_compiler.utils.Log;
import com.example.router_compiler.utils.Utils;
import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author: raotong
 * @data：2020/12/21 17:26
 * @Description :
 */

@AutoService(Processor.class)
/**
 * 适用于java版本
 * */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
/**
 * 注解类型
 * */
@SupportedAnnotationTypes(Consts.ANN_TYPE_ROUTE)
public class RouteProcessor extends AbstractProcessor {

    /**
     * 文件生成器
     */
    private Filer fileUtils;
    private Log log;

    private Types typeutils;

    // 节点工具
    private Elements elementUtils;

    /**
     * 分组
     * key :组名  value: 对应的组的路由信息
     */
    private Map<String, List<RouteMeta>> groupMap = new HashMap<>();

    /**
     * 初始化
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        log = Log.newLog(processingEnv.getMessager());
        fileUtils = processingEnv.getFiler();
        typeutils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();


    }

    /**
     * @param set      使用支持处理注解的节点集合
     * @param roundEnv 表示当前活之前的环境
     * @return true 表示处理器不在会处理（已处理过）
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        if (!Utils.isEmpty(set)) {
            /**
             * 获取到我们用Route标记的元素集合
             * */
            Set<? extends Element> annotatedWith = roundEnv.getElementsAnnotatedWith(Route.class);
            /**
             * 处理routr注解
             * */
            if (!Utils.isEmpty(annotatedWith)) {
                log.i("route class==" + annotatedWith.size());
                parseRoute(annotatedWith);

            }


            return true;
        }
        return false;
    }

    // 处理注解
    private void parseRoute(Set<? extends Element> annotatedWith) {
        // 类节点信息
        TypeElement typeElement = elementUtils.getTypeElement(Consts.ACTIVITY);
        TypeMirror type_activity = typeElement.asType();
        for (Element element : annotatedWith) {
            // 获取使用注解的类信息
            RouteMeta routeMeta;
            TypeMirror tm = element.asType();
            Route route = element.getAnnotation(Route.class);

            //  判断事发后是标注在Activity类上的注解
            if (typeutils.isSubtype(tm, type_activity)) {
                routeMeta = new RouteMeta(RouteMeta.Type.ACTIVITY, route, element);


            } else {
                throw new RuntimeException(" JUst support Activity/service " + element);
            }
            /**
             *
             * 路由信息的记录
             * */
            categories(routeMeta);

        }

    }

    private void categories(RouteMeta routeMeta) {
        if (routeVerify(routeMeta)) {
            List<RouteMeta> routeMetas = groupMap.get(routeMeta.getGroup());

        } else {
            log.i("Group info Erorr " + routeMeta.getPath());
        }


    }

    private boolean routeVerify(RouteMeta routeMeta) {
        String path = routeMeta.getPath();
        String group = routeMeta.getGroup();
        if (Utils.isEmpty(path) || !path.startsWith("/")) {
            return false;
        }
        if (Utils.isEmpty(group)) {
            String substring = path.substring(1, path.indexOf("/", 1));
            if (Utils.isEmpty(substring)) {
                return false;
            }
            routeMeta.setGroup(group);
            return true;

        }

        return true;


    }
}
