var shell = require('shelljs');
const cp = require("child_process");
const fs = require("fs");
const path = require('path');

async function num(args){
    const mutator = args['mutator']
    console.log(args)
    sh_path = './lib/num.sh' + " " + mutator
    const result = shell.exec(sh_path)
    // 检查执行结果
    if (result.code !== 0) {
        console.error('\x1B[31m%s\x1B[0m', '[ERROR] an error occurred: ' + result.stderr);
        return;
    }
}

module.exports = (...args) => {
    return num(...args)
}