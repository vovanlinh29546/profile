import * as React from 'react';
import {ScrollView, Text, View, StyleSheet,ToastAndroid, ImageBackground, Image, TextInput, Dimensions, TouchableOpacity,KeyboardAvoidingView,TouchableWithoutFeedback,Alert } from 'react-native';

import { ProgressDialog } from '../node_modules/react-native-simple-dialogs';
import firebaseConfig from '../firebase/firebase.js';
import bgimage from '../images/backgroundreact.jpg';
import logo from '../images/logo2.png';
import Icon from 'react-native-vector-icons/Ionicons';

import styles from '../styles/styleLogin.js';
export default class LoginScreen extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      email:'',
      password:'',
      showPass:true,
      press:false,
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

getData=(email,password)=>{
  if ( email != '' && password != '' ) {
    this.setState({
      email:'',
      password:''
    })
    this.openProgress(true);
    this.onLogin();
  }else{
  
    Alert.alert('Lỗi! ', 'Vui lòng nhập đầy đủ thông tin!',[],{ cancelable: true });
  }

}

onLogin = () => {
const { email, password } = this.state;
firebaseConfig.auth().signInWithEmailAndPassword(email, password)
  .then((user) => {
    // If you need to do anything with the user, do it here
    this.props.navigation.navigate('Main', {
      email: email,

          })
    this.openProgress(false);

  })
  .catch((error) => {
    const { code, message } = error;
    // representation of the error
    this.openProgress(false),
   Alert.alert('Lỗi! ', 'Vui lòng đăng nhập lại',[],{ cancelable: true })
  });
}
  showPass = () => {
    if (this.state.press == false) {
      this.setState({ showPass: false, press: true })
    } else {
      this.setState({ showPass: true, press: false })
    }
  }

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
          <Text style={styles.logotext}>APP Login</Text>
        </View>
        <ScrollView keyboardShouldPersistTaps="handled"style={styles.inputcontainerall} >
        <KeyboardAvoidingView style={styles.inputcontainerall} behavior="position" enabled>
      <TouchableWithoutFeedback style={styles.inputcontainerall}>
        <View style={styles.inputcontainerall}>


        <View style={styles.inputcontainer}>
          
          <Icon name={'ios-mail'} size={28} color={'white'}
                style={styles.inputicon}/>
          <TextInput
            style={styles.input}
            placeholder="Username"
            placeholderTextColor={'white'}
            returnKeyType="next"
            underlineColorAndroid='transparent'
            onChangeText={(email) => this.setState({email})}
                  value={this.state.email}
                 
                  keyboardType='email-address'
                
          />

        </View >
        <View style={styles.inputcontainer}>
       
          <Icon name={'ios-lock'} size={28} color={'white'}
                style={styles.inputicon}/>
          <TextInput
            style={styles.input}
            placeholder="Password"
            secureTextEntry={this.state.showPass}
            placeholderTextColor={'white'}
            underlineColorAndroid='transparent'
            onChangeText={(password) => this.setState({password})}
            value={this.state.password}
          />
         
          <TouchableOpacity style={styles.btneye} onPress={this.showPass.bind(this)}>
                <Icon name={this.state.press == false ? 'ios-eye' : 'ios-eye-off'} size={26} color={'white'}  />
                </TouchableOpacity>
        </View>

 


        <View style={styles.inputcontainer}>
        <TouchableOpacity style={styles.btnlogin}
           onPress={this.getData.bind(this,this.state.email,this.state.password)}  >
          <Text style={styles.textlogin}>Login</Text>
        </TouchableOpacity>
        </View>
        <Text style={styles.textregis} onPress={() =>this.props.navigation.navigate('Register')}
        >Bạn chưa có tài khoản?</Text>
       
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
