package pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter.TireWithDetailsWebCommandConverter;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationInput;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.OperationResult;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Status;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.operation.Type;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireSaveService;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateTireExecutor implements OperationExecutor {

	private final TireSaveService tireSaveService;
	private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

	@Transactional
	@Override
	public OperationResult execute(OperationInput operationInput) {
		try {
			TireDetails tireDetails = tireWithDetailsWebCommandConverter.toTireDetails(operationInput.getTireWithDetailsWebCommand());
			tireSaveService.save(tireDetails);
			return new OperationResult(Type.ADD, Status.SUCCESS, List.of());
		} catch (Exception e) {
			// TODO: słabe to catchowanie wszystkiego, przysłaniasz błędy
			return new OperationResult(Type.ADD, Status.FAILURE, List.of());
		}
	}

	@Override
	public boolean support(Type type) {
		return type == Type.ADD;
	}

}
