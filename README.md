# ParkImagePicker
Android simple image picker library.

## Preview
<table border = 0>
    <tr>
      <td><img width = "300" src = "https://user-images.githubusercontent.com/47319426/80021797-081b4100-8516-11ea-853f-c3caea901d39.jpg"></td>
      <td></td>
      <td><img width = "300" src = "https://user-images.githubusercontent.com/47319426/80021868-284b0000-8516-11ea-9cfc-142726b70ce1.jpg"></td>        
    </tr>
    <tr>
        <td align='center'>Demo Activity</td>
        <td></td>
        <td align='center'>Result view</td>
    </tr>
</table>
<table border = 0>
    <tr>
      <td><img width = "300" src = "https://user-images.githubusercontent.com/47319426/80021822-15383000-8516-11ea-9594-e1137f7c964f.jpg"></td>
      <td></td>
      <td><img width = "300" src = "https://user-images.githubusercontent.com/47319426/80021850-208b5b80-8516-11ea-8e72-ff379d1578b8.jpg"></td>
    </tr>
    <tr>
        <td align='center'>ParkImagePicker(Multi)</td>
        <td></td>
        <td align='center'>ParkImagePicker(Single)</td>
    </tr>
    
</table>


## How to use
1. Add maven repository.
<pre>
maven {
    url 'https://dl.bintray.com/parksm/maven/'
}
</pre>
2. Add to build.gradle
<pre>
implementation 'com.smparkworld.parkimagepicker:parkimagepicker:2.0.3'
</pre>

## Basic example
<pre>
// Default type is multi-picker.
ParkImagePicker.create(context).start();
</pre>

## General example
- Single image picker.
<pre>
ParkImagePicker.create(this)
               .setOnSelectedListener(new ParkImagePicker.OnSingleSelectedListener() {
                   @Override
                   public void onImageSelected(String uri) {
                        // Please enter the code
                   }
               })
               .start();               
</pre>
<br> 

- Multi image picker.
<pre>
ParkImagePicker.create(this)
               .setOnSelectedListener(new ParkImagePicker.OnMultiSelectedListener() {
                   @Override
                   public void onImageSelected(List&lt;String&gt; uri) {
                        // Please enter the code
                   }
               })
               .start();           
</pre>

## Options
<pre>
// Use it if you want to use single-picker.
.setOnSelectedListener(new ParkImagePicker.OnSingleSelectedListener() {
    @Override
    public void onImageSelected(String uri) {
    
    }
})
// Use it if you want to use multi-picker. Default type is multi-picker.
.setOnSelectedListener(new ParkImagePicker.OnMultiSelectedListener() {
    @Override
    public void onImageSelected(List&lt;String&gt; uri) {
    
    }
})
.setNumOfColumns(4)                   // default value is 3.
.setTakePictureBtn(true)              // default value is true.
.setTaskPictureBtnIcon(R.drawable.?)
.setTitle("Sample title")
.setTitleFontColor(R.color.?)
.setTitleBackgroundColor(R.color.?)
</pre>


## License
<pre>
Copyright 2020 ParkSM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```
</pre>
