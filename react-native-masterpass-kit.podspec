require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-masterpass-kit"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-masterpass-kit
                   DESC
  s.homepage     = "https://github.com/r0b0t3d/react-native-masterpass-kit"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Tuan Luong" => "tuanluong.it@gmail.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/r0b0t3d/react-native-masterpass-kit.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.vendored_frameworks = "ios/Frameworks/MasterPassKit.framework"
  # ...
  # s.dependency "..."
end

