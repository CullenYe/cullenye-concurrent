package com.cullenye.concurrent.ch2tool.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 遍历指定目录（含子目录）寻找指定类型文件
 * @author yeguanhong
 * @date 2020-06-18 00:13:20
 */
public class FindDirsFiles extends RecursiveAction {

    /**
     * 当前任务需要搜寻的目录
     */
    private File path;

    public FindDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        // 每一个路径都需要一个FindDirsFiles来处理
        List<FindDirsFiles> listFindDirsFiles  = new ArrayList<>();

        File[] files = path.listFiles();

        if(files!=null){
            for(File file : files)
            {
                if(file.isDirectory())
                {
                    listFindDirsFiles.add(new FindDirsFiles(file));
                }
                else
                {
                    if(file.getAbsolutePath().endsWith("java"))
                    {
                        System.out.println("文件："+file.getAbsolutePath());
                    }
                }
            }
            if(!listFindDirsFiles.isEmpty())
            {
                /*invokeAll(listFindDirsFiles);
                for(FindDirsFiles findDirsFiles : listFindDirsFiles)
                {
                    findDirsFiles.join();
                }*/
                for(FindDirsFiles findDirsFiles : invokeAll(listFindDirsFiles))
                {
                    findDirsFiles.join();
                }

            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FindDirsFiles findDirsFiles = new FindDirsFiles(new File("F:\\IdeaProject"));
        // 异步提交任务，提交后马上返回，继续往下执行其它工作，不会阻塞
        forkJoinPool.execute(findDirsFiles);

        System.out.println("Task is Running......");
        int otherWork = 0;
        for(int i=0;i<100;i++)
        {
            otherWork = otherWork+i;
        }
        System.out.println("Main Thread done sth......,otherWork="+otherWork);

        findDirsFiles.join();
        System.out.println("Task end");
    }
}
