import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import getters from './getters'
import createPersistedState from 'vuex-persistedstate'
import permission from './modules/permission'

Vue.use(Vuex)

const modulesFiles = require.context('./modules', false, /\.js$/)

// you do not need `import app from './modules/app'`
// it will auto require all vuex module from modules file
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // set './app.js' => 'app'
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  modules[moduleName] = value.default
  return modules
}, {})

const store = new Vuex.Store({
  modules: {
    app,
    user,
    permission
  },
  getters,
  // ���ˢ��vuex״̬��ʧ����
  plugins: [createPersistedState({
    storage: window.sessionStorage,
    reducer(val) {
      return {
        // ֻ����state�е�assessmentData
        dict: val.dict
      }
    }
  })]
})

export default store
