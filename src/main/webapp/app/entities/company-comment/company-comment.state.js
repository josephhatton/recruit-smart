(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('company-comment', {
        parent: 'entity',
        url: '/company-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'CompanyComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/company-comment/company-comments.html',
            controller: 'CompanyCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('company-comment-detail', {
        parent: 'company-comment',
        url: '/company-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'CompanyComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/company-comment/company-comment-detail.html',
            controller: 'CompanyCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'CompanyComment', function ($stateParams, CompanyComment) {
            return CompanyComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'company-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('company-comment-detail.edit', {
        parent: 'company-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-comment/company-comment-dialog.html',
            controller: 'CompanyCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['CompanyComment', function (CompanyComment) {
                return CompanyComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('company-comment.new', {
        parent: 'company-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-comment/company-comment-dialog.html',
            controller: 'CompanyCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('company-comment', null, {reload: 'company-comment'});
          }, function () {
            $state.go('company-comment');
          });
        }]
      })
      .state('company-comment.edit', {
        parent: 'company-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-comment/company-comment-dialog.html',
            controller: 'CompanyCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['CompanyComment', function (CompanyComment) {
                return CompanyComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('company-comment', null, {reload: 'company-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('company-comment.delete', {
        parent: 'company-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/company-comment/company-comment-delete-dialog.html',
            controller: 'CompanyCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['CompanyComment', function (CompanyComment) {
                return CompanyComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('company-comment', null, {reload: 'company-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
