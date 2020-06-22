import { StyleSheet } from 'react-native';
import {Dimensions, } from 'react-native';


const { width, height } = Dimensions.get('window')
export default StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        justifyContent: 'center',
        width: null,
        height: null,
    },
    postStatus: {
        backgroundColor: '#432577',
        marginTop: 30,
    },
    postContainer: {
        backgroundColor: '#f1f1f1',
        margin: width * 3.6 / 187.5,
        padding: width * 3.6 / 187.5,
        borderRadius: width * 3.6 / 187.5,
    },
    postRow: {
        padding: 5,

        fontSize: 16,
    
    
    },
    postText: {
        color: 'blue',
        fontSize: 20,
        
    },
    nameAuth: {
        color: '#000',
        fontSize: 16
    },
    postText: {
        color: '#000',
        // backgroundColor:'#fff',
        padding: width * 3.6 / 187.5
    },
    insertContainer: {
        width: width * 167.5 / 187.5,

        backgroundColor: '#fff',
        padding: width * 5 / 187.5
    },
    inputcontainerall:{
        width: '100%',
      
      },
    insertRow: {
        flexDirection: 'row', alignItems: 'center',
        marginBottom: 20,
    },
    inserttext: {
        width: '22%',
        textAlign: "left",
        paddingLeft: 5,

    },
    insertTextinput: {
        height: 34,
        flex: 1,
        marginRight: 4,
        borderWidth: 0.1,
        borderRadius: 5,
        backgroundColor: 'white',
        paddingLeft: 5,

    },
    btninsert: {
        marginTop: 30,
        height: 45,
        borderRadius: 15,
        backgroundColor: '#432577',
        justifyContent: 'center',
        borderColor: 'white',
        borderWidth: 0.5,
    },
    textinsert: {
        color: 'white',
        fontSize: 20,
        textAlign: 'center',

    },
    pickimage: {
        borderColor: 'black',
        borderWidth: 0.5,
        marginLeft:40,
        justifyContent: 'center',
        backgroundColor: '#f1f1f1',
        width:150,
        height:120,
    },
    postbooks:{
        flex: 1,

        flexDirection: 'row',
    
        fontSize: 18,
    





     

    },
    showimage: {
        borderColor: 'black',
        borderWidth: 0.5,
        marginLeft:0,
        margin:10,
        justifyContent: 'center',
        backgroundColor: '#f1f1f1',
        width:80,
        height:80,
    },
    sticond: {
        position: 'absolute',
        right: 1
    },
    sticonu: {
        position: 'absolute',
        right: 40
    },
  });
  