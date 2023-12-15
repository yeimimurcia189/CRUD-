const express = require('express');
const mongoose = require('mongoose');

const app = express();

mongoose.connect('mongodb://localhost:27017/myapp', {useNewUrlParser: true, useUnifiedTopology: true});

const UserSchema = new mongoose.Schema({
  name: String,
  email: String,
  phone: String
});

const User = mongoose.model('User', UserSchema);

app.use(express.json());

app.get('/users', async (req, res) => {
  const users = await User.find();
  res.send(users);
});

app.post('/users', async (req, res) => {
  const newUser = new User(req.body);
  const user = await newUser.save();
  res.send(user);
});

app.put('/users/:id', async (req, res) => {
  const user = await User.findByIdAndUpdate(req.params.id, req.body);
  res.send(user);
});

app.delete('/users/:id', async (req, res) => {
  await User.findByIdAndDelete(req.params.id);
  res.send('User deleted');
});

app.listen(3000, () => console.log('Server listening on port 3000'));
