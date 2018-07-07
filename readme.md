# 用户服务
- 用户登录
- 用户注册
- 用户信息获取

# 课程服务
- 登录验证            
- 课程的crud

# 信息服务
- 短信验证
- 邮件验证

# 用户edgeService 
- 用户登录
   - 描述：单点登录 无session
   - A. 验证缓存中的token
   - B. 验证密码 
- 用户注册
    - A. 发送验证
    - B. 新增用户
    - C. 生成并返回token 缓存Redis
- 用户信息获取
    - 通过id获取用户信息
    - 通过用户名获取用户信息（登录验证）
    
# 课程edgeService
- 描述：调用课程微服务的课程crud

# Api GATEWAY
- zuul: 网关