namespace java com.faxe.thrift.user

struct UserInfo {
	1:i32 id,
	2:string username,
	3:string password,
	4:string realName,
	5:string mobile,
	6:string phoneNumber
}

service UserService {
	UserInfo getUserById(1:i32 id);

	UserInfo getUserByUserName(1:string username);

	void registerUser(1:UserInfo userInfo);
}
