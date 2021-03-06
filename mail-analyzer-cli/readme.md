#mail-analyzer-cli

## Introduction
This program is a Command Line Interface (CLI) which takes as input:
* The phishing-rules json file generated by `rules-aggregator-api`
* A folder containing several `*.eml` files to scan

All the email files are scanned by the CLI, using the rules.
* The valid emails are recopied as-is in output folder
* The emails that are obviously phishing are NOT recopied to output folder
* The emails for which we have a doubt are _sanitized_ : 
 * the attachments are removed, 
 * the `href` attributes are removed from `a` HTML tags
 * A warning is added at the top of the body of the email (_WARNING - THIS EMAIL IS CONSIDERED AS PHISHING BY POSEIDON_)
 
 In case of phishing detected, the details of the email are inserted into H2 database (temporary database for this Hackathon, in real life we would of course use a real database). Then the `threat-response-api` uses the details to take the appropriate actions.

 The application is based on Picocli, a lightweight and useful CLI interface developed in Java. See https://picocli.info/ for more.
 
 **Advantage of using a CLI:** it's easily scalable by incrementing the number of parallel threads that analyze the emails. We could then theorically process hundreds/thousands of emails very quickly, in a lightweight footprint.

## How to compile and run
Package de whole application with:
> mvn clean package

Then run the CLI with the following:
> java -cp target\mail-analyzer-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar lu.goc2022.MailAnalyzerCli

without arguments, which provides you the following help message:

> Missing required options: '--in=<inDirectory>', '--out=<outDirectory>'
Usage: analyze -i=<inDirectory> -o=<outDirectory> [-t=<threadCount>]
  -i, --in=<inDirectory>     Folder containing input EML files
  -o, --out=<outDirectory>   Folder that will contain output EML files
  -t, --thread=<threadCount> Number of parallel threads to start (must be >= 1 and <= 10)
  
 So, relaunch the CLI and this time provide the missing required arguments:
> java -cp target\mail-analyzer-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar lu.goc2022.MailAnalyzerCli -i src/main/resources/in -o src/main/resources/out -t 3

You then get some logs similar to:
```
[main] INFO lu.goc2022.cli.command.analyze.AnalyzeCommand - Input directory: src\main\resources\in
[main] INFO lu.goc2022.cli.command.analyze.AnalyzeCommand - Output directory: src\main\resources\out
[main] INFO lu.goc2022.cli.command.analyze.AnalyzeCommand - Number of thread(s): 3
[main] INFO lu.goc2022.cli.command.analyze.AnalyzeCommand - Getting last version of phishing rules...
[main] INFO lu.goc2022.cli.command.analyze.AnalyzeCommand - Preparing all analysis tasks...
[main] INFO lu.goc2022.cli.command.analyze.AnalyzeCommand - --> 11 analysis to perform
[main] INFO lu.goc2022.cli.command.analyze.AnalyzeCommand - Executing all 11 analysis tasks on 3 thread(s)...
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 131.eml - hostScore: 0.0 /  global score: 0.0
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 12.eml - hostScore: 0.0 /  global score: 0.0
[pool-1-thread-1] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 103.eml - hostScore: 0.0 /  global score: 0.5
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 131.eml: OK
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 12.eml: OK
[pool-1-thread-1] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 103.eml: DELETE
[pool-1-thread-1] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask -   --> Email ignored as recognized as phishing: src\main\resources\in\103.eml
[pool-1-thread-1] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 149.eml - hostScore: 0.0 /  global score: 0.43333333333333335
[pool-1-thread-1] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 149.eml: SANITIZE
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 185.eml - hostScore: 0.0 /  global score: 0.0
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 185.eml: OK
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 202.eml - hostScore: 0.0 /  global score: 0.0
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 202.eml: OK
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 4.eml - hostScore: 0.0 /  global score: 0.43333333333333335
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 4.eml: SANITIZE
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 167.eml - hostScore: 0.0 /  global score: 0.0
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 167.eml: OK
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 40.eml - hostScore: 0.0 /  global score: 0.0
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 40.eml: OK
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 69.eml - hostScore: 0.0 /  global score: 0.0
[pool-1-thread-2] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 69.eml: OK
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider -  --> 8.eml - hostScore: 0.0 /  global score: 0.43333333333333335
[pool-1-thread-3] INFO lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask - Decision for 8.eml: SANITIZE
```


