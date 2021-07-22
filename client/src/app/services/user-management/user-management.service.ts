export class UserManagementService {
  loggedInUser = {
    username: '',
  };

  constructor() {}

  initSync = () => {
    let userInfo: string | null = '';
    if ((userInfo = localStorage.getItem('userInfo'))) {
      const userObj = JSON.parse(userInfo);
      const authType = localStorage.getItem('authType');
      if (authType === 'google') {
        this.setUsername(userObj.name);
      } else if (authType === 'github') {
        this.setUsername(userObj.login);
      }
    }
  }

  setUsername = (name: string) => {
    this.loggedInUser.username = name;
  };

  logout = () => {
    localStorage.removeItem('userInfo');
    localStorage.removeItem('access_token');
    localStorage.removeItem('authType');
    this.setUsername('');
  }
}
