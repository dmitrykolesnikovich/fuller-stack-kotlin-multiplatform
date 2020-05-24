package dependencyinjection

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import sample.Counter
import sample.CounterImpl
import usecases.FetchNotesListUseCaseAsync

val common = Kodein.Module("Common") {
    bind<Counter>() with singleton { CounterImpl() }
    bind() from singleton { FetchNotesListUseCaseAsync() }
}
