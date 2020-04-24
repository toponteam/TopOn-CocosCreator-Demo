#!/usr/bin/evn python
# coding: UTF-8
__author__ = 'nate'

import os
import shutil
import sys
import subprocess
import zipfile


def cur_file_dir():
    path = sys.path[0]
    if os.path.isdir(path):
        return path
    elif os.path.isfile(path):
        return os.path.dirname(path)


def runcmd(cmd):
    print '\rrun cmd: ' + cmd + '\r'
    ret = os.system(cmd)
    print '\nEnd run cmd!!!' + ',, ret = %d' % ret + '\n'
    if ret != 0:  # run system cmd error, see terminal stderr
        print '\rstep %s failed' % cmd
        sys.exit(0)
    print '\rstep %s success' % cmd


# gradle
ROOT_DIR = cur_file_dir()


Output_Dir = "cocoscreator_sdk_output/"
Android_Output_Dir = Output_Dir + "Android/"
iOS_Output_Dir = Output_Dir + "iOS/"
Android_iOS_Output_Dir = Output_Dir + "Android_iOS/"


Script_Dir = "Script/AnyThinkAds"
Bridge_Android = "bridge_android"
Bridge_iOS = "bridge_ios"

SDK_Version = "v5.5.6"


def buildCrossPlatform():
    shutil.copytree(ROOT_DIR + '/demo_cocoscreator/assets/Script/AnyThinkAds', ROOT_DIR + '/' + Android_iOS_Output_Dir + Script_Dir)
    shutil.copytree(ROOT_DIR + '/AndroidCocosCreatorBridge/anythink_bridge_aar/src/main/java', ROOT_DIR + '/' + Android_iOS_Output_Dir + 'bridge_android/source')
    shutil.copytree(ROOT_DIR + '/iOSCocosCreatorBridge', ROOT_DIR + '/' + Android_iOS_Output_Dir + 'bridge_ios')

def buildAndroid():
    shutil.copytree(ROOT_DIR + '/demo_cocoscreator/assets/Script/AnyThinkAds', ROOT_DIR + '/' + Android_Output_Dir + Script_Dir)
    shutil.rmtree(ROOT_DIR + '/'+ Android_Output_Dir + '/'+ Script_Dir + '/iOS')

    shutil.copytree(ROOT_DIR + '/AndroidCocosCreatorBridge/anythink_bridge_aar/src/main/java', ROOT_DIR + '/' + Android_Output_Dir + 'bridge_android/source')


def buildiOS():
    shutil.copytree(ROOT_DIR + '/demo_cocoscreator/assets/Script/AnyThinkAds', ROOT_DIR  + '/' + iOS_Output_Dir + Script_Dir)
    shutil.rmtree(ROOT_DIR + '/' + iOS_Output_Dir + Script_Dir + '/Android')

    shutil.copytree(ROOT_DIR + '/iOSCocosCreatorBridge', ROOT_DIR + '/' + iOS_Output_Dir + 'bridge_ios')

def main():
    # 打印结果
    print 'current py dir: ' + ROOT_DIR
    os.chdir(ROOT_DIR)

    if os.path.exists('./' + Output_Dir):
        shutil.rmtree('./' + Output_Dir)

    buildAndroid()
    buildiOS()
    buildCrossPlatform()

    for root , dirs, files in os.walk(ROOT_DIR + '/' + Output_Dir):
        for name in files:
            if name.endswith(".meta"):
                os.remove(os.path.join(root, name))


    zip_path('./' + Output_Dir, ROOT_DIR, 'TopOn_CocosCreator_SDK_' + SDK_Version + "_release.zip")


def dfs_get_zip_file(input_path,result):

    #
    files = os.listdir(input_path)
    for file in files:
        if os.path.isdir(input_path+'/'+file):
            dfs_get_zip_file(input_path+'/'+file,result)
        else:
            result.append(input_path+'/'+file)

def zip_path(input_path,output_path,output_name):

    f = zipfile.ZipFile(output_path+'/'+output_name,'w',zipfile.ZIP_DEFLATED)
    filelists = []
    dfs_get_zip_file(input_path,filelists)
    for file in filelists:
        f.write(file)
    f.close()
    return output_path+r"/"+output_name


if __name__ == '__main__':
    main()
