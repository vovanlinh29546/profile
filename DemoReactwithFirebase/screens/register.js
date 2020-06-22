import React from 'react';
import { StyleSheet, Text, View,Image,TextInput,Dimensions,ImageBackground,
TouchableOpacity,ToastAndroid,KeyboardAvoidingView,TouchableWithoutFeedback,Alert,ScrollView } from 'react-native';
import bgimage from '../images/backgroundreact.jpg';
import logo from '../images/logo2.png';

import firebaseConfig from '../firebase/firebase.js';
import { ProgressDialog } from 'react-native-simple-dialogs';


import Icon from 'react-native-vector-icons/Ionicons';
import styles from '../styles/styleLogin.js';

//Register
export default class RegisterScreen extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      email:'',
      password:'',
      cofirm:'',
      showPass:true,
      press:false,
      showPass1:true,
      press1:false,
      showProgress:false,

    };
  }

  openProgress = (kt) => {
          this.setState({ showProgress: kt });
  }
  

  showPass=()=>{
    if (this.state.press == false) {
      this.setState({showPass:false,press:true})
    }else{
      this.setState({showPass:true,press:false})
    }
  }
  showPass1=()=>{
    if (this.state.press1 == false) {
      this.setState({showPass1:false,press1:true})
    }else{
      this.setState({showPass1:true,press1:false})
    }
  }

  getData=(email,cofirm)=>{
    if ( email != '' && cofirm != '' && this.state.password != '' ) {
      if (cofirm == this.state.password) {
        this.setState({
          email:'',
          password:'',
          cofirm:'',
        })
        //come back screen login
        //come back screen login
        this.openProgress(true);
        this.onRegister();
      }else{
        ToastAndroid.show('Vui lòng nhập chính xác mật khẩu!', ToastAndroid.SHORT);
      }
    }else{
      ToastAndroid.show('Vui lòng nhập đầy đủ thông tin!', ToastAndroid.SHORT);
    }

  }

  onRegister = () => {
  const { email, cofirm } = this.state;
  firebaseConfig.auth().createUserWithEmailAndPassword(email, cofirm)
    .then((user) => {
      // If you need to do anything with the user, do it here
      // The user will be logged in automatically by the
      // `onAuthStateChanged` listener we set up in App.js earlier

        Alert.alert('Finish! ', 'Đăng kí thành công!',[],{ cancelable: true });
        this.openProgress(false),
        this.props.navigation.navigate('Main', {
          email: email,
    
              })
    })
    .catch((error) => {
      const { code, message } = error;
      // For details of error codes, see the docs
      // The message contains the default Firebase string
      // representation of the error
      this.openProgress(false);
     Alert.alert('Lỗi! ', 'Vui lòng thử lại',[],{ cancelable: true })

    });
  }

  //setup toolbar and status bar

  render() {

    return (

      <ImageBackground
      source={
         bgimage}
      style={styles.backgroundcontainer}>
        <View style={styles.logocontainer}>
          <Image
            source={logo}
            style={styles.logo}
          />
          <Text style={styles.logotext}>APP Register</Text>
        </View>
        <ScrollView keyboardShouldPersistTaps="handled" >
 <KeyboardAvoidingView style={styles.inputcontainerall} behavior="position" enabled>
      <TouchableWithoutFeedback style={styles.inputcontainerall}>
        <View style={styles.inputcontainerall}>

          <View style={styles.inputcontainer}>
            <Icon name={'ios-mail'} size={28} color={'white'}
              style={styles.inputicon}/>
              <TextInput
                style={styles.input}
                placeholder={'Email'}
                placeholderTextColor={'white'}
                underlineColorAndroid='transparent'

                keyboardType='email-address'
                returnKeyType="next"
                onChangeText={(email) => this.setState({email})}
                value={this.state.email}
              />
          </View>



          <View style={styles.inputcontainer} >
            <Icon name={'ios-lock'} size={28} color={'white'}
              style={styles.inputicon}/>
              <TextInput
                style={styles.input}
                placeholder={'Password'}
                secureTextEntry={this.state.showPass}
                placeholderTextColor={'white'}
                underlineColorAndroid='transparent'

                returnKeyType="next"
                onChangeText={(password) => this.setState({password})}
                value={this.state.password}
              />

              <TouchableOpacity style={styles.btneye} onPress={this.showPass.bind(this)}>
              <Icon name={this.state.press == false ? 'ios-eye' : 'ios-eye-off'} size={26} color={'white'}  />
              </TouchableOpacity>
          </View>



          <View style={styles.inputcontainer} >
            <Icon name={'ios-lock'} size={28} color={'white'}
              style={styles.inputicon}/>
              <TextInput
                style={styles.input}
                placeholder={'Cofirm Password'}
                secureTextEntry={this.state.showPass1}
                placeholderTextColor={'white'}
                underlineColorAndroid='transparent'

                returnKeyType="done"
                onChangeText={(cofirm) => this.setState({cofirm})}
                value={this.state.cofirm}
              />

              <TouchableOpacity style={styles.btneye} onPress={this.showPass1.bind(this)}>
              <Icon name={this.state.press1 == false ? 'ios-eye' : 'ios-eye-off'} size={26} color={'white'}  />
              </TouchableOpacity>
          </View>

  

      <View >
        <TouchableOpacity style={styles.btnlogin} onPress={this.getData.bind(this,this.state.email,this.state.cofirm)} >
          <Text style={styles.textlogin}>Register</Text>
        </TouchableOpacity>

        <Text style={styles.textregis} onPress={() =>this.props.navigation.navigate('Login')}
        >Go to Login?</Text>
      </View>
      </View>
        </TouchableWithoutFeedback>
      </KeyboardAvoidingView >
      </ScrollView>
      <ProgressDialog
          title="Loading"
          activityIndicatorColor="blue"
          activityIndicatorSize="large"
          animationType="fade"
          message="Please, wait..."
          visible={ this.state.showProgress }
      />

</ImageBackground>
    );
  }
}
