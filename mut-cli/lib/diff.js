var shell = require('shelljs');
const cp = require("child_process");
const fs = require("fs");
const path = require('path');

async function diff(args){
    const mutator = args['mutator']
    const num = args['number']
    console.log(args)
    console.log(num)
    sh_path = './lib/diff.sh' + " " + mutator + " " + num
    const result = shell.exec(sh_path)
}

module.exports = (...args) => {
    return diff(...args)
}