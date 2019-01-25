[![Build Status](https://travis-ci.org/rnadler/json-processor.svg?branch=master)](https://travis-ci.org/rnadler/json-processor)
# json-processor

Process a JSON file

## Ruby Usage

    $ ./ruby/json_processor.rb input_file [output_file]

## Clojure Usage

    $ lein run input_file [output_file]
    
or

    $ lein uberjar
    $ java -jar ./target/uberjar/json-processor-0.1.0-SNAPSHOT-standalone.jar input_file [output_file]

## Options

- **input_file** (with or without a .json extension)
- **output_file** (optional - output is sent to stdout if not present)

## Examples

**Ruby**:

    $ ./ruby/json_processor.rb ./test/resources/base.json
    {
      "baseString": "zero",
      "level1": {
        "level1Float": 88.7,
        "level1String": "1",
        "level1Boolean": true,
        "level2": {
          "level2Integer": 222,
          "level2Float": 77.65
        },
        "level1Array": ["item0","item1"]
      },
      "baseNull": null
    }
    $
    
**Clojure**: 

    $ lein run ./test/resources/base output.json
    Wrote output.json
    $


### Run Clojure Tests

    $ lein test

## License
[MIT](https://tldrlegal.com/license/mit-license)
