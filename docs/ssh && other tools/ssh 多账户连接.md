## GitHub 多账号 SSH 配置文档

### 一、原理

SSH 客户端通过 `~/.ssh/config` 按主机别名区分不同账号。给同一台服务器（github.com）起两个不同的 Host 别名，每个别名指定自己的密钥文件，git 就能按地址自动选用对应的私钥。

### 二、现有文件

```
~/.ssh/
├── id_ed25519        第一个账号私钥（Juno-Watre）
├── id_ed25519.pub
├── id_ed25519-382    第二个账号私钥（MuPinku）
├── id_ed25519-382.pub
└── config
```

### 三、config 内容（追加到 `~/.ssh/config`）

```
# GitHub 第一个账号（默认）
Host github.com
  HostName github.com
  User git
  IdentityFile ~/.ssh/id_ed25519
  IdentitiesOnly yes

# GitHub 第二个账号（用别名 github-382 访问）
Host github-382
  HostName github.com
  User git
  IdentityFile ~/.ssh/id_ed25519-382
  IdentitiesOnly yes
```

各字段含义：

| 字段 | 作用 |
|---|---|
| `Host` | 别名，git 地址里写什么就匹配什么 |
| `HostName` | 真实连接的服务器，两个别名都指向 github.com |
| `User git` | GitHub SSH 固定用 `git` 用户 |
| `IdentityFile` | 该别名使用的私钥 |
| `IdentitiesOnly yes` | 只用指定的这一个密钥，防止 ssh-agent 自动尝试其他密钥导致登错账号 |

### 四、验证

```bash
ssh -T git@github.com     # → Hi Juno-Watre!
ssh -T git@github-382     # → Hi MuPinku!
```

（提示 "GitHub does not provide shell access" 且 exit code 1 属正常现象。）

### 五、使用方法

**克隆新仓库**——把地址中的 `github.com` 换成对应别名：

```bash
git clone git@github.com:Juno-Watre/repo.git      # 账号一
git clone git@github-382:MuPinku/repo.git         # 账号二
```

**已有仓库切换账号**：

```bash
git remote set-url origin git@github-382:MuPinku/repo.git
```

**提交身份**：SSH 只管推送权限，提交记录上的作者身份要在每个仓库里单独设置，避免用错：

```bash
git config user.name  "MuPinku"
git config user.email "MuPinku@users.noreply.github.com"
```

（建议用 GitHub 提供的 `用户名@users.noreply.github.com` 邮箱保护隐私；不加 `--global` 就只对当前仓库生效。）

### 六、以后加第三个账号

1. 生成新密钥：`ssh-keygen -t ed25519 -f ~/.ssh/id_ed25519-xxx`
2. 把 `.pub` 内容添加到该 GitHub 账号的 Settings → SSH keys
3. 在 config 里复制一段，改 `Host github-xxx` 和 `IdentityFile` 即可