import { Link } from 'react-router-dom'

function Home() {
  return (
    <>
      <h1>Net Banking</h1>
      <div className="flex flex-col gap-6 m-16">
        {/* <button className='bg-blue-900'>Login</button> */}
        <Link to='/login' className='bg-blue-900 py-3 rounded-md text-neutral-50 hover:text-zinc-400'>Login</Link>
        <Link to='/register' className='bg-zinc-900 py-3 rounded-md text-neutral-50'>Register</Link>
      </div>
    </>
  )
}

export default Home