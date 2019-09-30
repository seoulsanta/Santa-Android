# Santa-Android
서울특별시 등산코스 안내 모바일 어플리케이션


<img width="1060" alt="01" src="https://user-images.githubusercontent.com/24809669/65867683-d6902c00-e3b1-11e9-99bc-07d095f4d144.png">

## 개발환경 및 사용 언어

- 안드로이드 스튜디오 3.4.2

- Kotlin

## Dependencies

    //Design, Layout, etc.
    implementation 'com.google.android.material:material:1.0.0-rc01'
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    implementation "androidx.recyclerview:recyclerview:1.0.0"
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'

    //indicator
    implementation 'com.ryanjeffreybrooks:indefinitepagerindicator:1.0.10'
    implementation 'com.tbuonomo.andrui:viewpagerdotsindicator:4.1.2'

    //permission
    implementation "gun0912.ted:tedpermission:2.1.0"
    
    //KaKao API 
    implementation 'androidx.appcompat:appcompat:1.1.0-alpha01'
    implementation files('libs/libDaumMapAndroid.jar')

    implementation group: project.KAKAO_SDK_GROUP, name: 'usermgmt', version: project.KAKAO_SDK_VERSION
    implementation 'com.kakao.sdk:kakaotalk:1.13.0'

## 화면 및 기능 설명

<img width="777" alt="03" src="https://user-images.githubusercontent.com/24809669/65867691-da23b300-e3b1-11e9-88a8-4eddb6a189cf.png">
<img width="777" alt="04" src="https://user-images.githubusercontent.com/24809669/65867694-dabc4980-e3b1-11e9-8038-e665b8db2de1.png">
<img width="777" alt="05" src="https://user-images.githubusercontent.com/24809669/65867700-dc860d00-e3b1-11e9-8a31-d0c301f5889d.png">
<img width="777" alt="06" src="https://user-images.githubusercontent.com/24809669/65867701-dd1ea380-e3b1-11e9-8801-bc9bcabac4f3.png">
<img width="777" alt="07" src="https://user-images.githubusercontent.com/24809669/65867712-e3ad1b00-e3b1-11e9-9323-0a43d0ab8cd6.png">
<img width="777" alt="08" src="https://user-images.githubusercontent.com/24809669/65867714-e4de4800-e3b1-11e9-8bf0-c17c8f2fd48b.png">
<img width="777" alt="09" src="https://user-images.githubusercontent.com/24809669/65867715-e60f7500-e3b1-11e9-9a62-a0f93ad5a294.png">
