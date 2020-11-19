记录git移除历史提交文件的小办法.  
对于git历史提交的文件,假如在本地`add`,`commit`了一些大文件,在推送到远程时出现413(文件过大)限制,可以执行命令将对应文件从git所有提交历史中移除.  
如大文件 "src/resources/爱的供养.mp3", 可以执行 **git filter-branch --tree-filter 'rm -f src/resources/爱的供养.mp3' HEAD** 来将大文件
从历史提交中移除.(稳妥起见应先备份项目)
