import React from 'react'
import {
    View,
    Text,
    StyleSheet,
    Dimensions,
    TextInput,
    Button,
    FlatList,
    Alert,
    ListView,
    TouchableOpacity,
    Image,
    KeyboardAvoidingView,
    TouchableWithoutFeedback,ScrollView
} from 'react-native'
import Dialog from "react-native-dialog";
import firebase from '../firebase/firebase.js';
import Modal from 'react-native-modal'
import * as ImagePicker from 'expo-image-picker';
import Constants from 'expo-constants';
import * as Permissions from 'expo-permissions';
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';
import styles from '../styles/MainStyle';
import { ProgressDialog } from 'react-native-simple-dialogs';
import imgaaddpic from '../images/imgaaddpic.jpg';

export default class MainScreen extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            postContent: '',
            post: [],
            modalVisible: false,
            modalVisibleUpdate: false,
            dialogVisible: false,
            currentItem:'',
            showProgress:false,

        }
        this.itemsRef = firebase.database().ref().child('books');
    }
    openProgress = (kt) => {
        this.setState({ showProgress: kt });
}
    getPermissionAsync = async () => {
        if (Constants.platform.ios) {
            const { status } = await Permissions.askAsync(Permissions.CAMERA_ROLL);
            if (status !== 'granted') {
                alert('Sorry, we need camera roll permissions to make this work!');
            }
        }
    }
    _showDialog = () => {
        this.setState({ modalVisible: true })
    }
    _hideDialog = () => {
        this.setState({ modalVisible: false })
    }
    _showDialogUpdate = (ds) => {
       
        this.setState({currentItem: ds})
        this.setState({modalVisibleUpdate: true})
    }
    _hideDialogUpdate = () => {
        this.setState({ modalVisibleUpdate: false })
    }
    _post = () => {
        this.setState({ modalVisible: true })
    }
    _showDialogdelete = () => {
        this.setState({ dialogVisible: true });
    };

    _handleCanceldelete = () => {
        this.setState({ dialogVisible: false });
    };

    listenForItems(itemsRef) {
        itemsRef.on('value', (snap) => {
            var items = [];
            snap.forEach((child) => {
                let item = {
                    id: (child.key),
                    ten: child.val().ten,
                    gia: child.val().gia,
                    tacgia: child.val().tacgia,
                    soluong: child.val().soluong,
                    nxb: child.val().nxb,
                    image: child.val().image
                }
                items.push(item);
            });


            this.setState({
                post: items
            });
            this.state.post.map((item, idx) => {
                console.log(item.id)
            })

        });
    }
    deleteAddress(key) {
        Alert.alert(
          'Delete Address',
          'Are you sure want to delete this address ?',
          [
            {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
            {text: 'OK', onPress: () => this. _delete(key)},
          ],
          { cancelable: false }
        )
     
      }
    _delete(key) {
        
        firebase.database().ref().child('books').child(key).remove()
            .then((user) => {
            
                this.setState({ dialogVisible: false });
                Alert.alert('Finish! ', 'Xóa thành công!', [], { cancelable: true });

            })
            .catch((error) => {
                const { code, message } = error;
                // For details of error codes, see the docs
                // The message contains the default Firebase string
                // representation of the error

                Alert.alert('Lỗi! ', 'Vui lòng thử lại', [], { cancelable: true })

            });
    }

    _edit(key) {
        firebase.database().ref().child('books').child(key).set({
            ten: 'post',
            gia: '200',
            tacgia: 'da update',
            soluong: 'da update',
            nxb: 'da update',
        })

    }

    componentDidMount() {
        this.getPermissionAsync();
        this.listenForItems(this.itemsRef)
    }

    render() {
        return (
            <View style={styles.container}>
                <Modal
                    animationType="slide"
                    transparent={false}
                    isVisible={this.state.modalVisible}
                    onBackButtonPress={this._hideDialog}
                    onBackdropPress={this._hideDialog}>

                    <InsertProduct />
                </Modal>
                <View style={styles.btninsert}>
                    <TouchableOpacity
                        onPress={() => {
                            this._showDialog()
                        }}  >
                        <Text style={styles.textinsert}>Insert</Text>
                    </TouchableOpacity>


                </View>
                <FlatList
                    data={this.state.post}

                    renderItem={({ item }) =>

                        <View style={styles.postContainer}>
                            <View
                                style={styles.postbooks}
                            >
                                <Image source={{ uri: item.image }} style={styles.showimage} />
                                <View style={styles.postRow}>

                                    <Text>{item.ten}</Text>

                                    <Text>{item.tacgia}</Text>

                                    <Text>{item.nxb}</Text>

                                    <Text>{item.soluong}</Text>

                                    <Text>{item.gia}</Text>
                                </View>


                                <TouchableOpacity
                                    style={styles.sticond}
                                    onPress={() => {
                                        this.deleteAddress(item.id)
                                    }}


                                >
                                    <MaterialIcons name={'delete'} size={26} color={'blue'} />
                                </TouchableOpacity>






                                <TouchableOpacity
                                    style={styles.sticonu}
                                    onPress={() => {
                                        this._showDialogUpdate(item)
                                    }}

                                >

                                    <MaterialIcons name={'update'} size={26} color={'blue'} />

                                </TouchableOpacity>

                                <Modal
                                    animationType="slide"
                                    transparent={false}
                                    isVisible={this.state.modalVisibleUpdate}
                                    onBackButtonPress={this._hideDialogUpdate}
                                    onBackdropPress={this._hideDialogUpdate}>

                                   
                                    <UpdateProduct item = {this.state.currentItem}  />
                                </Modal>

                            </View>


                        </View>


                    }
                    keyExtractor={item => item.id}
                />
   <ProgressDialog
          title="Loading"
          activityIndicatorColor="blue"
          activityIndicatorSize="large"
          animationType="fade"
          message="Please, wait..."
          visible={ this.state.showProgress }
      />
            </View>
        )

    }
}




export class InsertProduct extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            ten: '',
            tacgia: '',
            soluong: '',
            gia: '',
            nxb: '',
            image: '0',
            showProgress:false,

        }
    }
    openProgress = (kt) => {
        this.setState({ showProgress: kt });
}
    async _chooseImage() {

        let result = await ImagePicker.launchImageLibraryAsync()
        if (!result.cancelled) {
            this.setState({ image: result.uri })
        }
    }
    createID() {
        return Date.now().valueOf();
    }
    async _uploadImage(uri) {
        this.setState({ isLoading: true })

        const response = await fetch(uri)
        const blob = await response.blob()

        const ID = this.createID()
        let ref = firebase.storage().ref().child("IMG" + ID)
        let uploadTask = ref.put(blob)
        uploadTask.on('state_changed',
            (snapshot) => {
                this.openProgress(true);
                let progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;    
            }, (error) => {
                console.log(error)
            },
            () => {
                 uploadTask.snapshot.ref.getDownloadURL().then((downloadURL) => {
               
                    this._post(downloadURL, ID);
                });
            }
        )
    }
    _post = async (image1, ID) => {

        if (this.state.ten.length == 0 || this.state.gia.length == 0 || this.state.tacgia.length == 0 || this.state.nxb.length == 0 || this.state.soluong.length == 0) {
            alert("Không được để trống thông tin");
            return;
        }
        else {
            firebase.database().ref().child('books/' + ID).set({
                image: image1,
                ten: this.state.ten,
                tacgia: this.state.tacgia,
                soluong: this.state.soluong,
                gia: this.state.gia,
                nxb: this.state.nxb,
            })
                .then((user) => {
                    this.ten.clear();
                    this.gia.clear();
                    this.tacgia.clear();
                    this.soluong.clear();
                    this.nxb.clear();
                    this.setState({ image: '0' })
                    this.openProgress(false);
                    Alert.alert('Finish! ', 'Thêm thành công!', [], { cancelable: true });

                })
                .catch((error) => {
                    const { code, message } = error;
                    // For details of error codes, see the docs
                    // The message contains the default Firebase string
                    // representation of the error

                    Alert.alert('Lỗi! ', 'Vui lòng thử lại', [], { cancelable: true })

                });
        }

    }


    render() {
        //let { image } = this.state;
        return (
            <View style={styles.insertContainer}>
<ScrollView keyboardShouldPersistTaps="handled" >
<KeyboardAvoidingView style={styles.inputcontainerall}

             behavior={"padding"} enabled>

      <View style={styles.inputcontainerall}>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Tên sách:</Text>
                    <TextInput
                        style={styles.insertTextinput}
                        returnKeyType="next"
                        underlineColorAndroid='transparent'
                        onChangeText={(value) => { this.setState({ ten: value }) }}
                        ref={input => { this.ten = input }}
          
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Giá:</Text>
                    <TextInput
                        style={styles.insertTextinput}
                        returnKeyType="next"
                        underlineColorAndroid='transparent'
                        onChangeText={(value) => { this.setState({ gia: value }) }}
                        ref={input => { this.gia = input }}
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Tác giả:</Text>
                    <TextInput
                        style={styles.insertTextinput}
                        returnKeyType="next"
                        underlineColorAndroid='transparent'
                        onChangeText={(value) => { this.setState({ tacgia: value }) }}
                        ref={input => { this.tacgia = input }}
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Nhà xuất bản:</Text>
                    <TextInput
                        style={styles.insertTextinput}
                        returnKeyType="next"
                        underlineColorAndroid='transparent'
                        onChangeText={(value) => { this.setState({ nxb: value }) }}
                        ref={input => { this.nxb = input }}
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Số lượng:</Text>
                    <TextInput
                        style={styles.insertTextinput}
                        returnKeyType="next"
                        underlineColorAndroid='transparent'
                        onChangeText={(value) => { this.setState({ soluong: value }) }}
                        ref={input => { this.soluong = input }}
                    />
                </View>


                <View style={styles.insertRow}>

                    <Button
                        title="Pick image"
                        onPress={() => this._chooseImage()}
                    />

                    <Image source={
                this.state.image == '0' ? imgaaddpic : { uri: this.state.image }
                } style={styles.pickimage} />

                </View>
      

                <View style={styles.btninsert}>
                    <TouchableOpacity
                        onPress={() => { this._uploadImage(this.state.image) }}>
                        <Text style={styles.textinsert}>Save</Text>
                    </TouchableOpacity>


                </View>
                </View>

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
            </View>
        )
    }
}

export class UpdateProduct extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            ten: this.props.item.ten,
            tacgia: this.props.item.tacgia,
            soluong: this.props.item.soluong,
            gia: this.props.item.gia,
            keyupdate: this.props.item.id,
            nxb: this.props.item.nxb,
            image_Update:this.props.item.image,
            showProgress:false,
        }
    }
    openProgress = (kt) => {
        this.setState({ showProgress: kt });
}

 async _chooseImageUpdate(){
    let result = await ImagePicker.launchImageLibraryAsync()
    if(!result.cancelled){
        this.setState({image_Update:result.uri})
    }
}

async _uploadImageUpdate(uri){
        this.setState({isLoading:true})
        const response = await fetch(uri)
        const blob = await response.blob()
        const ID = this.props.item.id
        let ref = firebase.storage().ref().child("IMG"+ID)
        let uploadTask = ref.put(blob)
        uploadTask.on('state_changed',
            (snapshot)=>{
                this.openProgress(true);
                let progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            },(error)=>{
                console.log(error)
            },
            ()=>{
                uploadTask.snapshot.ref.getDownloadURL().then((downloadURL)=> {  
                    this._edit(this.state.keyupdate , downloadURL)
                });       
            }
        )
}



    _edit(key,image) {


        firebase.database().ref().child('books').child(key).update({
            image:  image,
            ten: this.state.ten,
            tacgia: this.state.tacgia,
            soluong: this.state.soluong,
            gia: this.state.gia,
            nxb: this.state.nxb
        }).then((user) => {
            this.openProgress(false);
            Alert.alert('Finish! ', 'Sửa thành công!', [], { cancelable: true });

        })
            .catch((error) => {
                const { code, message } = error;
                // For details of error codes, see the docs
                // The message contains the default Firebase string
                // representation of the error

                Alert.alert('Lỗi! ', 'Vui lòng thử lại', [], { cancelable: true })

            });
    }

    render() {
        return (

            <View style={styles.insertContainer}>
                <ScrollView keyboardShouldPersistTaps="handled" >
<KeyboardAvoidingView style={styles.inputcontainerall}

             behavior={"padding"} enabled>

      <View style={styles.inputcontainerall}>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Tên sách:</Text>
                    <TextInput
                        style={styles.insertTextinput}

                        onChangeText={(value) => { this.setState({ ten: value }) }}
                        ref={input => { this.ten = input }}
                        value={this.state.ten}
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Giá:</Text>
                    <TextInput
                        style={styles.insertTextinput}

                        onChangeText={(value) => { this.setState({ gia: value }) }}
                        ref={input => { this.gia = input }}
                        value={this.state.gia}
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Tác giả:</Text>
                    <TextInput
                        style={styles.insertTextinput}

                        onChangeText={(value) => { this.setState({ tacgia: value }) }}
                        ref={input => { this.tacgia = input }}
                        value={this.state.tacgia}
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Nhà xuất bản:</Text>
                    <TextInput
                        style={styles.insertTextinput}

                        onChangeText={(value) => { this.setState({ nxb: value }) }}
                        value={this.state.nxb}
                    />
                </View>
                <View style={styles.insertRow}>
                    <Text style={styles.inserttext}>Số lượng:</Text>
                    <TextInput
                        style={styles.insertTextinput}

                        onChangeText={(value) => { this.setState({ soluong: value }) }}
                        value={this.state.soluong}
                    />
                </View>
                <View style={styles.insertRow}>

                    <Button
                        title="Pick image"
                        onPress={() =>this._chooseImageUpdate()}
                    />

                    <Image source={{ uri: this.state.image_Update }} style={styles.pickimage} />

                </View>
    
                <View style={styles.btninsert}>
                    <TouchableOpacity
                        onPress={() => { this._uploadImageUpdate(this.state.image_Update)}}>
                        <Text style={styles.textinsert}>Save</Text>
                    </TouchableOpacity>


                </View>
                </View>

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
            </View>
        )
    }
}
