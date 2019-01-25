#!/usr/bin/env ruby
require 'json'
require_relative 'process_json'

def usage
  "usage: json_processor.rb input_json [output_json]"
end
if ARGV.length == 0
  puts 'Missing requred parameter.'
  puts usage
  exit 1
end

INPUT_FILE = ARGV[0]
OUTPUT_FILE = ARGV.length > 1 && ARGV[1].length > 0 ? ARGV[1] : nil

output = JSON.pretty_generate(ProcessJson.new(INPUT_FILE.dup).get_output)
if OUTPUT_FILE.nil?
  puts output
else
  File.open(OUTPUT_FILE, 'w') {|f| f.puts output}
  puts "Wrote #{OUTPUT_FILE}"
end
