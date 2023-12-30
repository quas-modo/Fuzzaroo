#! /usr/bin/env node
const program = require('commander')

program
  .version(`Version is ${require('../package.json').version}`)
  .description('NJUSE software testing homework cli tool')
  .usage('<command> [options]')
  .option('-b, --build')

program
    .command('build')
    .description('choose mutator and stage to run test')
    .option('-m, --mutator <type>', 'choose provided mutator -- abs, air, aor, lcr, ror, uoi, complex', 'uoi')
    .option('-s, --stage <stage>', 'choose mutating stage -- select, compile, score', 'select')
    .action((args, cmd) => {
        require('../lib/build')(args)
    })

  program
  .parse(process.argv)