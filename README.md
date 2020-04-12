# ParkImagePicker
Android simple image picker library.

# preview
<table border = 0>
    <tr>
      <td><img width = "242" height = "346" src = "https://user-images.githubusercontent.com/47319426/79068135-9ebe5580-7cff-11ea-9ab9-a791f5787ade.jpg"></td>
      <td></td>
      <td><img width = "242" height = "346" src = "https://user-images.githubusercontent.com/47319426/79068142-abdb4480-7cff-11ea-99a8-46fae143b018.jpg"></td>
      <td></td>
      <td><img width = "242" height = "346" src = "https://user-images.githubusercontent.com/47319426/79068148-b4cc1600-7cff-11ea-94c9-dab22a5a810d.jpg"></td>
    </tr>
    <tr>
        <td align='center'>Demo Activity</td>
        <td></td>
        <td align='center'>ParkImagePicker</td>
        <td></td>
        <td align='center'>Selected image</td>
    </tr>
</table>


# How to use
1. Add maven repository.
<pre>
maven {
    url 'https://dl.bintray.com/parksm/maven/'
}
</pre>
2. Add to build.gradle
<pre>
implementation 'com.smparkworld.parkimagepicker:parkimagepicker:1.0.0'
</pre>


# Basic example
<pre>
new ParkImagePicker(context).show();
</pre>


# Options
<pre>
new ParkImagePicker(context)
    .setOnSelectedListener(new ParkImagePicker.OnImageSelectedListener() {
        @Override
        public void onImageSelected(String uri) {

        }
    })
    .setOnSelectedImageView(imageView)    // set image to ImageView.
    .setNumOfColumns(4)                   // default value is 3.
    .setTakePictureBtn(true)              // default value is true.
    .setTaskPictureBtnIcon(R.drawable.?)
    .setTitle("Sample title")
    .setTitleFontColor(R.color.?)
    .setTitleBackgroundColor(R.color.?)
    .show();
</pre>


# License
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
