/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  NativeModules,
  Platform,
  StyleSheet,
  Text,
  View,
  Button
} from 'react-native';

const { BackgroundTaskBridge } = NativeModules

export default class App extends Component<{}> {

  _handleButtonPress = () => {
    BackgroundTaskBridge.pinWidgetToHomeScreen();
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Button
         title="Add"
         onPress={this._handleButtonPress}
         />
        <Text style={styles.instructions}>
          To get started, edit App.js and widgetTask.js
        </Text>
        <Text style={styles.instructions}>
          Edit your native code and define the business logic in
          javascript
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
