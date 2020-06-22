import { StyleSheet } from 'react-native';
import {Dimensions, } from 'react-native';
const { width: WIDTH } = Dimensions.get('window')
export default StyleSheet.create({
    backgroundcontainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        width:null,
        height:null,
      },
      logocontainer: {
        alignItems: 'center',
        marginBottom: 20,
        marginTop: 30,
      },
    
      logo: {
        marginTop: 0,
        width: 135,
        height: 120,
    
      },
      logotext: {
        color: 'white',
        fontSize: 20,
        fontWeight: '500',
        marginTop: 10,
        opacity: 0.5,
        marginBottom: 10,
      },
      input: {
        width: WIDTH - 55,
        height: 45,
        borderRadius: 25,
        fontSize: 16,
        paddingLeft: 45,
        backgroundColor: 'rgba(0,0,0,0.35)',
        color: 'rgba(255,255,255,0.7)',
        marginHorizontal: 25,
        borderColor: 'white',
        borderWidth: 0.5,
      },
      inputcontainer: {
        marginTop: 20,
    
      },
      inputcontainerall:{
        width: '100%',
       
    
      },
      btnlogin: {
        marginTop: 20,
        width: WIDTH - 55,
        height: 45,
        borderRadius: 25,
        backgroundColor: '#432577',
        justifyContent: 'center',
        borderColor: 'white',
        borderWidth: 0.5,
      },
      textlogin: {
        color: 'white',
        fontSize: 16,
        textAlign: 'center',
    
      },
      textregis: {
        color: 'white',
        fontSize: 16,
        textAlign: 'center',
        marginTop: 10,
      },
      inputicon: {
        position: 'absolute',
        top: 8,
        left: 37,
      },
      btneye: {
        position: 'absolute',
        right: 37,
      top:8,
      
      },
      btnRegister:{
        width: '100%',
        paddingLeft: 5,
        paddingRight: 5,
      },
});