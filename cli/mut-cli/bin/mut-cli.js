#! /usr/bin/env node
const program = require('commander')

program
  .version(`Version is ${require('../package.json').version}`)
  .description('NJUSE software testing homework cli tool')
  .usage('<command> [options]')
  .option('-b, --build')
  .option('-n, --num')
  .option('-d, --diff')

program
    .command('build')
    .description('choose mutator and stage to run test')
    .option('-m, --mutator <type>', 'choose provided mutator -- abs, air, aor, lcr, ror, uoi', 'uoi')
    .option('-s, --stage <stage>', 'choose mutating stage -- select, compile, score', 'select')
    .option('-t, --testsuite <type>', 'choose testsuite, only needed in complex', 'complex')
    .action((args, cmd) => {
        require('../lib/build')(args)
    })

program
    .command('num')
    .description('the number of mutation')
    .option('-m, --mutator <type>', 'type the built mutator -- abs, air, aor, lcr, ror, uoi, complex', 'uoi')
    .action((args, cmd) => {
        require('../lib/num')(args)
    })

program
    .command('diff')
    .description('check the difference between origin and mutation')
    .option('-m, --mutator <type>', 'type the built mutator -- abs, air, aor, lcr, ror, uoi, complex', 'uoi')
    .option('--number <num>', 'choose the exact mutation', '1')
    .action((args, cmd) => {
        require('../lib/diff')(args)
    })

  program
  .parse(process.argv)