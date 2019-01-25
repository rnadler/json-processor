class ProcessJson
  JSON_EXT = '.json'

  def initialize(input_file)
    @input_file = input_file
    @dir_name = File.dirname(input_file)
    @json_cache = {}
  end
  
  def get_output
    process_json(get_json_content(@input_file))
  end

  private
  def get_json_content(file)
    file << JSON_EXT unless file.include?(JSON_EXT)
    File.open(file, 'r') { |f| @json_cache[file] = JSON.parse(f.read) } unless @json_cache.has_key?(file)
    @json_cache[file]
  end
  
  def process_json(obj)
    if obj.kind_of?(Hash)
      obj.clone.each { |k, v|
        if k.start_with?('include')
          obj.delete(k)
          get_json_content(File.join(@dir_name,v)).values.first
               .each { |k, o| obj[k] = process_json(o) }
        end
        process_json(v)
      }
    end
    obj
  end
end
