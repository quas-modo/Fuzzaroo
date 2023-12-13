var shell = require('shelljs');


const cp = require("child_process");
const fs = require("fs");

const path = require('path')

async function build(args) {
    const mutator = args['mutator']
    const stage = args['stage']
    console.log(args)
    if (!shell.which('java')) {
        shell.echo('Java version is not installed');
        shell.exit(1);
    }

    sh_path = ''
    output_path = './output_' + stage + '.log'

    if (stage == 'select'){
        sh_path = './lib/select.sh'
    }else if(stage == 'compile'){
        sh_path = './lib/compile.sh'
    }else if(stage == 'score'){
        sh_path = './lib/score.sh'
    }else{
        console.error('\x1B[31m%s\x1B[0m', '[ERROR]Unknown stage')
        return 
    }


    sh_path = sh_path + " " + mutator
    console.log(sh_path)

    shell.exec(sh_path, (code, stdout, stderr) => {
        if (code != 0) {
            console.log('\x1B[31m%s\x1B[0m', '[ERROR]Error in removing files');
            return;
        }
        if (stderr) {
            console.error('\x1B[31m%s\x1B[0m', '[ERROR]an error with file systems');
            return;
        }
        console.error('\x1B[36m%s\x1B[0m','[LOG]Result of shell script exectuion above')
        fs.writeFile(output_path, stdout, (writeError) => {
            if(writeError){
                console.error('\x1B[36m%s\x1B[0m','[ERROR] Error writign to file')
                return 
            }
            console.log('[LOG]Result of script execution saved to', path.resolve(output_path))
        })
    })

}

module.exports = (...args) => {
    return build(...args)
}